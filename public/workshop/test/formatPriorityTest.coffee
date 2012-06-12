#
require ['../src/script/lib/formatPriority'], (formatPriority) ->
  'use strict'

  $(document).ready ->

    #
    module 'Modul formatPriority'

    #
    test 'Funktion formatPriority()', ->
      equal formatPriority(0), 'Niedrig'
      equal formatPriority(1), 'Normal'
      equal formatPriority(2), 'Hoch'