#!/bin/sh
openssl genrsa -out /home/kali/stega_project/bob.pem 512;openssl pkey -in /home/kali/stega_project/bob.pem -out /home/kali/stega_project/public-key.pem -pubout