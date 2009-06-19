import re
import sys
dir = sys.argv[1]
f= open(dir + '/schemas.csv', 'r')
pat = re.compile('\w|\-')
index = 0
limit = int(sys.argv[2])
q = '"' //Set this to an empty string to remove quotes
def writeDDL(schemas):
	if len(schemas) < 2:
		return
	global index
	index = index + 1
	global dir
	f= open(dir + '/ddl/schema' + str(index) + '.ddl', 'w')
	out = 'CREATE TABLE ' + q + 'schema' + str(index) + q + ' ('
	for schema in schemas:
		out += (' ' + q + schema.replace('-','_') + q + ' VARCHAR(30),')
	out= out[0:-1] + ');'
	f.write(out + '\n')
	f.close()
	if index==limit:
		exit(0)
	
for line in f:
	split1 = line.partition(",{")
	numschemas = split1[0]
	split2 = split1[2].partition("},")
	occurrences = split2[2]
	schemas = split2[0].split("\,")
	
	if int(numschemas) > 4 and int(occurrences) > 1:
		l=[]
		for schema in schemas:
			if (schema[-1] != '*'):
				out = ''.join(pat.findall(schema))
				if (len(out) > 1):
					if out[-1] == '-':
						l.append(out[0:-1])
					elif out[0] == '-':
						l.append(out[1:])
					else:
						l.append(out)
		if len(l) > 1:
			writeDDL(l)
			
