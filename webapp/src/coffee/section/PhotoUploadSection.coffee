define ['base/RoomrSection', 'widget/PhotoUploadWidget'], (RoomrSection, PhotoUploadWidget) ->
  'use strict'

  class PhotoUploadSection extends RoomrSection

    constructor: ->
      super {
        name: 'photoUpload'
        title: 'Foto Upload'
      }

      photoUploadWidget = new PhotoUploadWidget()
      @addWidget photoUploadWidget
