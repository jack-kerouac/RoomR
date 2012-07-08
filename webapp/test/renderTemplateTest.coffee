# Für den Test muss erst mal das zuständige Modul geladen werden
require ['../src/script/base/renderTemplate'], (renderTemplate) ->
  'use strict'

  $(document).ready ->

    # `module()` teilt eine Testdatei in Module auf
    module 'Modul renderTemplate'

    test 'Funktion renderTemplate() ist eine Funktion', ->
      equal typeof renderTemplate, 'function'

    test 'Funktion renderTemplate()', ->
      stop()

      renderTemplate '../test/resources/test', {}, (html) ->
        ok html?
        equal html, '<p>TestTemplate</p>'
        start()

    test 'Funktion renderTemplate() Caching', ->
      stop()

      renderTemplate '../test/resources/test', {}, (html) ->
        start()

      fetchedHtml = undefined
      renderTemplate '../test/resources/test', {}, (html) ->
        fetchedHtml = html

      ok fetchedHtml?
      equal fetchedHtml, '<p>TestTemplate</p>'
