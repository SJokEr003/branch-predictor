#!/bin/bash

cp Predictor4800.java Predictor2400.java

perl -pi -e 's/Predictor4800/Predictor2400/g' Predictor2400.java

tar -zcvf archive.tar.gz Predictor1200.java Predictor2400.java Predictor4800.java

python test.py archive.tar.gz

 
