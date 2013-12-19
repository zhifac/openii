package org.mitre.schemastore.porters.schemaImporters.protobuf.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.mitre.schemastore.porters.schemaImporters.protobuf.parser.objects.PBEnumeration;
import org.mitre.schemastore.porters.schemaImporters.protobuf.parser.objects.ProtoParseException;
import org.mitre.schemastore.porters.schemaImporters.protobuf.parser.objects.RootScope;
import org.mitre.schemastore.porters.schemaImporters.protobuf.parser.objects.Scope;




import de.susebox.jtopas.CharArraySource;
import de.susebox.jtopas.Flags;
import de.susebox.jtopas.StandardTokenizer;
import de.susebox.jtopas.StandardTokenizerProperties;
import de.susebox.jtopas.Token;
import de.susebox.jtopas.Tokenizer;
import de.susebox.jtopas.TokenizerException;
import de.susebox.jtopas.TokenizerProperties;
import de.susebox.jtopas.TokenizerSource;

public class ProtobufParser {

	/**
	 * @param args
	 */
	 private static final Object UNIT_SCOPE    = "unit scope";
	   private static final Object OPTION_SCOPE  = "option scope";
	   private static final Object MESSAGE_SCOPE = "message scope";
	   private static final String BLOCK_BEGIN   = "{";
	   private static final String BLOCK_END     = "}";
	   private static final String OPTION_BEGIN = "["; 
	   private static final String OPTION_END   = "]"; 
	   private static TokenizerProperties prop;
	   public enum KeywordType { FREQUENCY, DATA_TYPE , OTHER}
	   public enum KeywordEnum { OPTIONAL(KeywordType.FREQUENCY, "optional" ), REQUIRED(KeywordType.FREQUENCY, "required"), REPEATED(KeywordType.FREQUENCY, "repeated"),
		   IMPORT("import"), MESSAGE("message"), ENUMERATION("enum"), PUBLIC("public"),
		   EXTENSIONS("extensions"), EXTEND("extend"), SERVICE("service"), RPC("rpc"),
		   DEPRECATED("deprecated"), OPTION("option"), TRUE("true"), FALSE("false"), PACKAGE("package"), TO("to")
		   ,STRING(KeywordType.DATA_TYPE, "string" ), BYTES(KeywordType.DATA_TYPE, "bytes"), BOOL(KeywordType.DATA_TYPE, "bool"), DOUBLE(KeywordType.DATA_TYPE, "double"), FLOAT(KeywordType.DATA_TYPE, "float"), INTEGER(KeywordType.DATA_TYPE, "int32","uint32", "sint32",
				   "fixed32", "sfixed32"), LONG(KeywordType.DATA_TYPE, "int64","uint64", "sint64","fixed64","sfixed64"), GROUP("group"), MAX("max");

	   	   private String[] keywords = null;
	   	   private KeywordType type = null;
	   	   private KeywordEnum(String... keywords)
	   	   {
	   		  this(KeywordType.OTHER, keywords );
	   	   }
	   	   private KeywordEnum(KeywordType type, String ... keywords ) {
	   		   this.keywords = keywords;
	   		   this.type = type;
	   	   }
	   	   private String[] getKeywords()
	   	   {
	   		   return keywords;
	   	   }
	   	   public KeywordType getType() {
	   		   return type;
	   	   }
	   };
	   
	private static synchronized TokenizerProperties getProperties() {
		if (prop == null) {
			prop = new StandardTokenizerProperties();
			prop.setParseFlags(Flags.F_RETURN_LINE_COMMENTS | Flags.F_RETURN_BLOCK_COMMENTS | Flags.F_COUNT_LINES | Flags.F_RETURN_IMAGE_PARTS );
			prop.setSeparators(null);
			prop.addSeparators(";");
			prop.addSeparators("=");
			prop.addLineComment("//");


		    prop.addString("\"", "\"", "\\");
		    prop.addString("'", "'", "\\");
		    for (KeywordEnum key : KeywordEnum.values()) {
		    	for (String keyword : key.getKeywords()) {

		    		prop.addKeyword(keyword, key);
		    	}
		    }
/*		    prop.addPattern("message\\s+([A-Za-z_][A-Za-z_0-9]*)", KeywordEnum.MESSAGE);
		    prop.addPattern("(u|s)?(int|fixed)32\\s+([A-Za-z_][A-Za-z_0-9]*)", KeywordEnum.INTEGER);
		    prop.addPattern("(u|s)?(int|fixed)64\\s+([A-Za-z_][A-Za-z_0-9]*)", KeywordEnum.LONG);
		    prop.addPattern("string\\s+([A-Za-z_][A-Za-z_0-9]*)", KeywordEnum.STRING);
		    prop.addPattern("bool\\s+([A-Za-z_][A-Za-z_0-9]*)", KeywordEnum.BOOL);
		    prop.addPattern("float\\s+([A-Za-z_][A-Za-z_0-9]*)", KeywordEnum.FLOAT);
		    prop.addPattern("double\\s+([A-Za-z_][A-Za-z_0-9]*))", KeywordEnum.DOUBLE);
		    prop.addPattern("bytes\\s+([A-Za-z_][A-Za-z_0-9]*)", KeywordEnum.BYTES);
		    prop.addPattern("enum\\s+([A-Za-z_][A-Za-z_0-9]*)", KeywordEnum.ENUMERATION);
		    */
	        prop.addSpecialSequence(BLOCK_BEGIN, UNIT_SCOPE);
	        prop.addSpecialSequence(BLOCK_END, UNIT_SCOPE);
	        prop.addSpecialSequence(OPTION_BEGIN, OPTION_SCOPE);
	        prop.addSpecialSequence(OPTION_END, OPTION_SCOPE);
		}
		return prop;
	}
	public RootScope parse(URI fileURI) throws URISyntaxException, IOException {


	        RootScope root = RootScope.getRootScope();
	        Scope currentScope = root;
	        String temp = fileURI.toString();
	        int index = temp.lastIndexOf("/");
	        URI parentURI = new URI(temp.substring(0,index));
	        parseFile(parentURI, fileURI, true, root); 
	  
		return root;
	}
	private void parseFile(URI parent, URI file, boolean isPublic, Scope scope ) throws IOException {
        ArrayList<String> tokens = new ArrayList<String>();		
        String line;
        StringBuffer fileContents = new StringBuffer();
        BufferedReader in = new BufferedReader(new FileReader(new File(file)));
		while ((line = in.readLine()) !=  null) { fileContents.append(line + "\n"); }
		in.close();
        Tokenizer tokenizer = new StandardTokenizer(getProperties());
        TokenizerSource source = new CharArraySource(fileContents.toString().toCharArray());
        boolean pastImports = false;
        boolean pastPackage = false;
        try {
        	tokenizer.setSource(source);

            // tokenize the file and print basically
            // formatted context to stdout
            while (tokenizer.hasMoreToken()) {
            	Token token = tokenizer.nextToken();
            	int type = token.getType();

            	if (!pastPackage) {
	            	// if we have a comment as our first token, we aren't going to include it
            		if (type == Token.BLOCK_COMMENT || type == Token.LINE_COMMENT) { continue; }

            		// if we have no tokens and we don't see something that is a keyword then we want to ignore it

            		if (type != Token.KEYWORD) { continue; } 
            	}
        		if (type == Token.KEYWORD) {
        			switch((KeywordEnum)token.getCompanion()) {
        			case PACKAGE: if (!pastPackage){
        				pastPackage = true;
        				if (tokenizer.hasMoreToken())
        				{	
        					Token nextToken = tokenizer.nextToken();
        					if (nextToken.getType() == Token.NORMAL){
        						scope = scope.getRoot().getNamespaceWithPackageName(nextToken.getImage());
        					}
        			}
        			else throw new ProtoParseException("Package name cannot come after imports, messages or enums in file");
        			}
        			break;
        			case IMPORT: if (!pastImports) {
        				pastPackage = true;
        				readImport(parent, tokenizer, scope);
        			}
        			else throw new ProtoParseException("Imports cannot come after messages or enums in file");
        			break;
        			case ENUMERATION:
        				pastPackage = true;
        				pastImports = true;
        				break;
        			case MESSAGE: 
        				pastPackage = true;
        				pastImports = true;
        				break;
        			case EXTEND:
        				pastPackage = true;
        				pastImports = true;
        				break;
        				
        			}
            	if (type == Token.SEPARATOR) {
            		if (tokens.size() > 0) {
	            		// add the list of tokens to our list of commands
            	//		commands.add(tokens);

            			// clear our list of tokens
            			tokens = new ArrayList<String>();
            		}
            	} else {
            		// extract the value from the command
            //		String value = ddl.substring(token.getStartPosition(), token.getStartPosition() + token.getLength());
            		String value = token.getImage();
            		// unless this is a string or a comment, make it upper case
            		if (type != Token.LINE_COMMENT && type != Token.BLOCK_COMMENT && type != Token.STRING) { value = value.toUpperCase(); }

            		// add it to our list
            		tokens.add(value);
            	}
            }
        } 	 }       catch (Throwable e) {
			e.printStackTrace();
		}finally {
            // never forget to release resources and references
            tokenizer.close();
        }



}
	public void parseMessage(Tokenizer tokenizer, Scope currentScope, boolean doesExtend){
		
	}
	public void parseEnum(Tokenizer tokenizer, Scope currentScope, String description) throws TokenizerException, ProtoParseException {
		Token nameToken = null;
		if (tokenizer.hasMoreToken()  && (nameToken = tokenizer.nextToken()).getType()==Token.NORMAL ) {
			PBEnumeration enumScope = new PBEnumeration(currentScope, nameToken.getImage(), description);
			int currentLine = nameToken.getEndLine();
		while (tokenizer.hasMoreToken()){
			Token nextToken = tokenizer.nextToken();
			
		}
		}
		else 
		{
			throw new ProtoParseException("Name must follow enumeration declaration");
		}
	}

	public void printTokens(URI fileURI) throws IOException, TokenizerException {

	       String line;
	        StringBuffer fileContents = new StringBuffer();
	        BufferedReader in = new BufferedReader(new FileReader(new File(fileURI)));
			while ((line = in.readLine()) !=  null) { fileContents.append(line + "\n"); }
			in.close();
	        Tokenizer tokenizer = new StandardTokenizer(getProperties());
	        TokenizerSource source = new CharArraySource(fileContents.toString().toCharArray());
	        tokenizer.setSource(source);
	        while (tokenizer.hasMoreToken()){
	        	Token token = tokenizer.nextToken();
	        	System.out.println("Token: " + Token.getTypeName(token.getType()) + " " + 
	        	token.getCompanion() + " " + token.getImage() + " line number: " + tokenizer.getLineNumber() + " column Number:" + tokenizer.getColumnNumber());
	        	if (token.getImageParts().length>1){
	        		System.out.print("     Parts: ");
	        		for (int i = 0; i < token.getImageParts().length; i++)
	        		{
	        			System.out.print( " " + i + ": " + token.getImageParts()[i]);
	        		}
	        		System.out.println();
	        	}
	        }
  

}
	
	public static void main(String[] args) throws IOException, URISyntaxException, TokenizerException {
		ProtobufParser parser = new ProtobufParser();
		StringBuffer proto = new StringBuffer();
		String line;
		File file = new File("C:\\Users\\mgreer\\Documents\\protobuf\\person.proto");
		URI uri = new URI("file:///C:/Users/mgreer/Documents/protobuf/person.proto");

		String[] directoryParts = uri.getPath().split("[/]");
	//	parser.parse(uri);
		parser.printTokens(uri);
		parser.parse(uri);


		

	}
	private Scope readPackageName(Tokenizer tokenizer, Scope currentScope) throws TokenizerException, ProtoParseException{
		ArrayList<Token> tokens = new ArrayList<Token>();
		Token token = null;
		while (tokenizer.hasMoreToken() && (token=tokenizer.nextToken()).getType() != Token.SEPARATOR){
			if (token.getType()!=Token.LINE_COMMENT){
				tokens.add(token);
			}
		}
		if (tokens.size()>1)
		{
			throw new ProtoParseException("Invalid package declaration");
		}
		if (tokens.size() ==0)
		{
			return currentScope;
		}
		return currentScope.getRoot().getNamespaceWithPackageName(tokens.get(0).getImage());
		
	
	}
	private void readImport(URI parentURI, Tokenizer tokenizer, Scope currentScope) throws TokenizerException, ProtoParseException, IOException, URISyntaxException{
		ArrayList<Token> tokens = new ArrayList<Token>();
		Token token = null;
		while (tokenizer.hasMoreToken() && (token=tokenizer.nextToken()).getType()!= Token.SEPARATOR) {
			if (token.getType()!= Token.LINE_COMMENT) {
				tokens.add(token);
			}
		}
		if (tokens.isEmpty()) return;
		boolean isPublic = false;
		if (tokens.get(0).getType()==Token.KEYWORD && tokens.get(0).getCompanion()==KeywordEnum.PUBLIC){
			isPublic = true;
			tokens.remove(0);
		}
		if (tokens.isEmpty())
			return;
		if (tokens.size()>1)
		{
			throw new ProtoParseException("Invalid import statement");
		}
		parseFile(parentURI, new URI(parentURI + "/"+tokens.get(0).getImage()), isPublic, currentScope.getRoot());
	}

}
