# Für den Test muss erst mal das zuständige Modul geladen werden
require ['../src/script/lib/formatPriority'], (formatPriority) ->
  'use strict'

  $(document).ready ->

    # `module()` teilt eine Testdatei in Module auf
    module 'Modul formatPriority'

    # In`test()` finden die eigentlichen Tests statt
    test 'Funktion formatPriority()', ->
      equal formatPriority(0), 'Niedrig'
      equal formatPriority(1), 'Normal'
      equal formatPriority(2), 'Hoch'