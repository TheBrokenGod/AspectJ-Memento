#!/bin/bash
find -follow -type f -name "*.java" > temp.lst 
find -follow -type f -name "*.aj" >> temp.lst
ajc -1.8 -argfile temp.lst -outjar $1
rm temp.lst
cp $(find -follow -type f -name "*.jar") $(dirname $1)
echo "#!/bin/bash" > $(dirname $1)/start.sh
echo "java -cp \"*\"" $2>> $(dirname $1)/start.sh
chmod +x $(dirname $1)/start.sh
echo "java -cp \"*\"" $2 >> $(dirname $1)/start.bat
