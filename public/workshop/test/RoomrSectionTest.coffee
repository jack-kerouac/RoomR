# Für den Test muss erst mal das zuständige Modul geladen werden
require ['../src/script/base/RoomrSection', 'lib/jqueryMock'], (RoomrSection, jqueryMock) ->
  'use strict'

  $(document).ready ->

    # `module()` teilt eine Testdatei in Module auf
    module 'Modul RoomrSection'

    # In`test()` finden die eigentlichen Tests statt
    test 'create RoomrSection', ->
      testSection = new RoomrSection {
        # dirty hack in order to load a test template
        name: 'test'
        title: 'test title'
      }

      returnedTemplateContent = 'HALLO'
      jqueryMock.mockAjax()

      stop()
      testSection.render -> 
        console.log(jqueryMock.url)

        selection = $('#test-section')
        equal selection.length, 1
        equal selection.text(), returnedTemplateContent
        start()

      # trigger end of ajax call
      jqueryMock.respond returnedTemplateContent
