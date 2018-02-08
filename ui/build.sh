#!/bin/bash
rm -R -f dist
mkdir dist

elm-make src/Main.elm --output=elm.js

cp index.html dist
cp *.css dist
cp elm.js dist
