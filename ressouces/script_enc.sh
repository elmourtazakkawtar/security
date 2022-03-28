#!/bin/sh
openssl rsautl -encrypt -in "$1" -inkey "$2" -pubin -out /home/kali/stega_project/encrypted_data