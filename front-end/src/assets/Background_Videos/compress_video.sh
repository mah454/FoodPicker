#!/bin/bash 

for I in *.mov
do
		FILE_NAME=$(echo $I | awk -F "." '{print $1}')
 		ffmpeg -i $I -vcodec libx265 -crf 20 $FILE_NAME.mp4
done
