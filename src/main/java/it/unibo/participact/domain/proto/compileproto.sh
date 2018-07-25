 #!/bin/bash

OUT_PROTO=/Users/andreacirri/Documents/workspace_android/participact-client/src/

for f in *.proto;
do
protoc -I=. --java_out=$OUT_PROTO ./$f
done
