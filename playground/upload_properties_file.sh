#! /bin/bash

if [ "$1" == "" ]; then
  echo "Missing file parameter (e.g. 'messages_nb.properties')"
  exit
fi

#curl http://localhost:8080/admin/insertUser
curl -u $(head -n 1 ~/.kgauth) -k -F file=@$1 http://localhost:8080/translator/admin/upload
#curl -u $(head -n 1 ~/.kgauth) -k -F file=@tilbud5.utf8 http://localhost:8080/postalDistrict/upload
