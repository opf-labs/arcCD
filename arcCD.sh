#!/bin/sh

# archCD version 0.1.0
# License GPL
# author Carl Wilson carl@openplanetsfoundation.org

# Add current dir to path
PATH=$PATH:.

# Welcome message and get the CD id
echo "Welcome to archCD, please insert a CD to archive."

# Check for a directory in the home music directory
rootDir="$USERPROFILE\\Music\\archCD"
# Does root dir exist?
if [[ ! -d "$rootDir" ]]
then
    if [[ -e "$rootDir" ]]
	then
	    echo "Root directory for archCD: $rootDir is an existing file NOT directory."
		exit
	fi
	mkdir $rootDir
fi

# Use cdrdao to find the cd device on the machine
# FIXME: This assumes that the machine has a single CD device
scanbus=$(cdrdao scanbus 2>&1)
echo "Checking CD drive...."
# regex to identify the device number
devregex="^[0-9],[0-9],[0-9] :.*$"
devnum=""
# loop through the output
while IFS= read -r
do
    # if the live starts with a cd device number then grab it
    if [[ $REPLY =~ $devregex ]]; then
        devnum=$(echo "$REPLY" | cut -d: -f1) 
    fi
done <<< "$scanbus"

# Check that we have a CD device,exit if not
if [ -z "$devnum" ]; then
    echo "No CD drive found on this machine, exiting."
	exit
fi

cdidOK=0
# loop until we get a numeric id
until [[ $cdidOK == 1 ]]
do
   echo "Please enter a numerical ID of 5 or less digits for the CD"
   read cdid
   if [[ $cdid =~ ^[0-9]+$ ]] && [[ ${#cdid} < 6 ]]; then
       cdidOK=1
   fi
done

# Pad cd id with leading zeros
printf -v cdid '%05d' $((10#$cdid))

# Look for prepared directory, create it if it doesn't exist
itemDir="$rootDir\\$cdid"
if [[ ! -d $itemDir ]]
then
    if [[ -e "$itemDir" ]]
	then
	    echo "Item directory for archCD: $itemDir is an existing file NOT directory."
		exit
	fi
	mkdir $itemDir
fi

# Search for the album info
infoFile="$itemDir\\$cdid.info"
if [[ ! -e "$infoFile" ]]
then
    echo "No info file: $infoFile found, please create it."
	exit
fi
infoTrackCount=0
echo "Checking track info file"
while IFS= read
do
	if [[ $REPLY =~ ^\[AlbumArtist\] ]]
	then
	    albumArtist="${REPLY:13}"
	elif [[ $REPLY =~ ^\[AlbumTitle\] ]]
	then
	    albumTitle="${REPLY:12}"
	elif [[ $REPLY =~ ^\[Track\] ]]
	then
		tracks[$infoTrackCount]=${REPLY:7}
	    infoTrackCount=$[infoTrackCount + 1]
	fi
done < "$infoFile"
echo "Checking CD track info, please wait...."
tocTrackCount=0
trackinfo=$(cdrdao read-toc --device $devnum --read-raw --fast-toc $cdid.toc 2>&1)
while IFS= read -r
do
    if [[ $REPLY =~ ^.[0-9] ]] || [[ $REPLY =~ ^[0-9][0-9] ]]
	then
	    tocTrackCount=$[tocTrackCount + 1]
	fi
done <<< "$trackinfo"
rm -f $cdid.toc

if [[ $infoTrackCount != $tocTrackCount ]]
then
    echo "There are $infoTrackCount tracks listed in $infoFile but $tocTrackCount in the CD TOC"
	exit
fi

echo "Artist: $albumArtist"
echo "Album : $albumTitle"
for track in "${tracks[@]}"
do
	echo "Track : $track" 
done
echo "Please confirm that details OK"
read confirm
isConfirmed=0
while [[ $isConfirmed == 0 ]]
do
  if [[ $confirm =~ ^[y|Y] ]]
  then
      isConfirmed=1
  elif [[ $confirm =~ ^[n|N] ]]
  then
      exit
  fi
done

echo "archiving CD....."

imageinfo=$(cdrdao read-cd --device $devnum --read-raw --paranoia-mode 3 -v 5 --datafile $cdid.bin $cdid.toc 2>&1)
while IFS= read -r
do
	echo "echo: $REPLY"
done <<< "$imageinfo"

mv $cdid.bin $itemDir\\$cdid.bin
mv $cdid.toc $itemDir\\$cdid.toc
