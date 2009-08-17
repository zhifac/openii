package org.mitre.schemastore.porters.schemaExporters.sql;


import java.util.Collection;
import java.util.HashMap;

public class RdbValueType implements Comparable<Object>
{
	private static int _nValues = 0;
	public final static RdbValueType VARCHAR = new RdbValueType( "VARCHAR", String.class );
	public final static RdbValueType INTEGER = new RdbValueType( "INTEGER", Integer.class );
	public final static RdbValueType NUMERIC = new RdbValueType( "NUMERIC", Float.class );
	public final static RdbValueType TEXT = new RdbValueType( "TEXT", String.class );
	public final static RdbValueType CHARACTER = new RdbValueType( "CHARACTER", String.class );
	public final static RdbValueType BOOLEAN = new RdbValueType( "BOOLEAN", Boolean.class );
//	public final static RdbValueType DATETIME = new RdbValueType( "DATETME", String.class);
	public final static RdbValueType TIMESTAMP = new RdbValueType( "TIMESTAMP", String.class );
	public final static RdbValueType VARCHAR255 = new RdbValueType( "VARCHAR(255)", String.class );
	public final static RdbValueType AUTO_INCREMENT = new RdbValueType( "SERIAL", Integer.class);
	public final static RdbValueType ANY = new RdbValueType( "ANY", Object.class);
	public final static RdbValueType ID = new RdbValueType( "ID", Integer.class);
	public static final RdbValueType FOREIGN_KEY = new RdbValueType( "INTEGER", Integer.class);
	
	private String _type;
	
	private static HashMap<String, RdbValueType> _values;
	private int _intValue;
	private Class<?> _javaType;

	public RdbValueType(String type, Class<?> javaType) {
		_type = type;
		_javaType = javaType;
		_intValue = _nValues++;
		
		if ( _values == null )
			_values = new HashMap<String, RdbValueType>();
		_values.put( _type, this );
		
	}

	public int compareTo( Object o ) {
		if (_intValue <  ((RdbValueType)o)._intValue) return -1;
		else if (_intValue <  ((RdbValueType)o)._intValue) return 1;
		else return 0;
	}

	public boolean equals( Object o ) {
		return (_intValue == ((RdbValueType)o).getIntValue());
	}

	public Class<?> getJavaType() {
		return _javaType;
	}

	public static Collection<RdbValueType> getValues() {
		return _values.values();
	}

	public int getIntValue() {
		return _intValue;
	}

	/**
	 * 
	 * @param s string representation of the database type
	 * @return RdbValueType designated by string
	 */
	public static RdbValueType valueOf( String s ) {
		RdbValueType type = (RdbValueType)_values.get( s );
		return type;
	}
	

	public String toString() {
		return _type;
	}

}