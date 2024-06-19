#!/usr/bin/env bash
java \
  -Dglass.platform=gtk \
  -Djava.library.path=/opt/javafx-sdk-21/lib \
  -Dmonocle.platform.traceConfig=false \
  -Dprism.verbose=false \
  -Djavafx.verbose=false \
  --module-path .:/opt/javafx-sdk-21/lib \
  --add-modules javafx.controls \
  --module energiequiz.fxgl/energiequiz.fxgl.FxglApp $@
