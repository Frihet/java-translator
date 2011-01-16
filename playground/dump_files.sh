#! /bin/bash

curl -u $(head -n 1 ~/.kgauth) -k http://localhost:8080/translator/admin/dump
