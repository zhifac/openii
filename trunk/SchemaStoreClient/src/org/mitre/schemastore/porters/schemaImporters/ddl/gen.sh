#/bin/bash
. setup.cmds
rm -f Sql*
cd SqlSQL2
java antlr.Tool -glib ../DmlSQL2/DmlSQL2.g -o .. SqlSQL2.g
#-trace -diagnostic
#cp ../Main.java .
cd -