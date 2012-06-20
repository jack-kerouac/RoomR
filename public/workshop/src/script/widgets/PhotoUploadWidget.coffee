define ['base/RoomrWidget', 'base/roomrUtil'], (RoomrWidget, roomrUtil) ->
  'use strict'

  class PhotoUploadWidget extends RoomrWidget
    nidus: undefined

    constructor: ->
      super('photoUpload')

    renderInto: (element) ->
      @nidus = $(element)
      @renderTemplate {}, (content) =>
        @nidus.html content
        @dropTarget = $('.drop-target', @nidus)

        $('.clickable', @nidus).on 'click', @uploadImages.bind this

        @fileSelect = $('input[type="file"]', @nidus)
        @fileSelect.on 'change', (changeEvent) =>
          changeEvent.preventDefault()
          @handleFiles @fileSelect[0].files

        roomrUtil.addDropHandler @dropTarget, (dropEvent) =>
          dt = dropEvent.dataTransfer;
          @handleFiles dt.files

    handleFiles: (files) ->
      _.each files, (file) =>
        return if !file.type.match /image\/.*/
        @renderImage file

    renderImage: (file) ->
      reader = new FileReader()
      reader.onload = (event) =>
        image = new Image()
        image.src = event.target.result
        image.onload = (event) =>
          img = event.target
          scale = Math.min @dropTarget.width() / img.width, @dropTarget.height() / img.height

          canvas = $('<canvas></canvas>')[0]
          canvas.width = img.width * scale
          canvas.height = img.height * scale

          context = canvas.getContext '2d'
          context.drawImage image, 0, 0, canvas.width, canvas.height

          @dropTarget.before canvas

      reader.readAsDataURL(file);

    uploadImages: ->
      BlobBuilder = BlobBuilder || WebKitBlobBuilder || MozBlobBuilder || MSBlobBuilder
      elements = $('canvas', @nidus)
      elements.each ->
        dataUrl = this.toDataURL()
        binary = atob dataUrl.replace(/^data:image\/(png|jpg);base64,/, "")
        contentType = dataUrl.match /image\/[^;]+/

        blobBuilder = new BlobBuilder()
        blobBuilder.append new Uint8Array(Array.prototype.map.call binary, (c) -> c.charCodeAt(0) & 0xff).buffer
        blob = blobBuilder.getBlob contentType

        formData = new FormData()
        formData.append 'image', blob

        $.ajax '/rest/postImage', {
          data: formData
          contentType: false
          processData: false
          type: 'POST'
          complete: (jqXHR, stat) ->
            if stat == 'success'
              console.log "Hat gepasst"
            else
              console.log "Kaputt", jqXHR
        }

