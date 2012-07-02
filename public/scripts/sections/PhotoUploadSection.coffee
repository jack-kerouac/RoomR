define ['base/RoomrSection.coffee#', 'widgets/PhotoUploadWidget.coffee#'], (RoomrSection, PhotoUploadWidget) ->
  'use strict'

  class PhotoUploadSection extends RoomrSection

    constructor: ->
      super {
        name: 'photoUpload'
        title: 'Foto Upload'
      }

      photoUploadWidget = new PhotoUploadWidget()
      @addWidget photoUploadWidget
