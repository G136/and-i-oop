#!/usr/bin/env bash
set -e

# without this i get a java.lang.ClassNotFoundException
cd src/main/java/

EXTLESS_TARGET="org/alorg/Heap"
OUTDIR="better-build"

mkdir -p $OUTDIR

javadoc $EXTLESS_TARGET.java -d $OUTDIR/docs/

javac $EXTLESS_TARGET.java
jar -cf $OUTDIR/Classes.jar $EXTLESS_TARGET.class
java -cp $OUTDIR/Classes.jar $EXTLESS_TARGET
