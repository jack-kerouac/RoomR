define ['base/RoomrSection', 'widgets/PhotoUploadWidget', 'widgets/FlatshareWidget'], (RoomrSection, PhotoUploadWidget, FlatshareWidget) ->
  'use strict'

  class TestSection extends RoomrSection

    constructor: ->
      super {
        name: 'test'
        title: 'Testbereich'
      }

      photoUploadWidget = new PhotoUploadWidget()
      @addWidget photoUploadWidget

      flatshareWidget = new FlatshareWidget()
      @addWidget flatshareWidget
