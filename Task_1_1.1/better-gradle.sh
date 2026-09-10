#!/usr/bin/env sh

EXTLESS_TARGET="src/main/java/org/alorg/Heap"
OUTDIR="better-result"

mkdir -p $OUTDIR

javac $EXTLESS_TARGET.java
jar -cf $OUTDIR/Classes.jar $EXTLESS_TARGET.class
java $OUTDIR/Classes.jar

javadoc $EXTLESS_TARGET.java -d $OUTDIR/docs/
