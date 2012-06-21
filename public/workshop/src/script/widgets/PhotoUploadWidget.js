(function() {
  var __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(['base/RoomrWidget', 'base/roomrUtil', 'modernizr'], function(RoomrWidget, roomrUtil) {
    'use strict';

    var PhotoUploadWidget;
    return PhotoUploadWidget = (function(_super) {
      var ImageCollection, ImageModel;

      __extends(PhotoUploadWidget, _super);

      ImageModel = Backbone.Model.extend({
        url: function() {
          return this.get('url');
        }
      });

      ImageCollection = Backbone.Collection.extend({
        model: ImageModel,
        url: '/rest/images'
      });

      PhotoUploadWidget.prototype.existingImages = new ImageCollection();

      PhotoUploadWidget.prototype.nidus = void 0;

      function PhotoUploadWidget() {
        PhotoUploadWidget.__super__.constructor.call(this, 'photoUpload');
      }

      PhotoUploadWidget.prototype.renderInto = function(element) {
        this.nidus = $(element);
        return this.render();
      };

      PhotoUploadWidget.prototype.render = function() {
        var _this = this;
        return this.existingImages.fetch({
          success: function() {
            return _this.renderTemplate({
              currentImages: _this.existingImages.models
            }, function(content) {
              var currentImageContainer;
              _this.nidus.html(content);
              _this.dropTarget = $('.drop-target', _this.nidus);
              $('.clickable', _this.nidus).on('click', _this.uploadImages.bind(_this));
              _this.fileSelect = $('input[type="file"]', _this.nidus);
              _this.fileSelect.on('change', function(changeEvent) {
                changeEvent.preventDefault();
                return _this.handleFiles(_this.fileSelect[0].files);
              });
              roomrUtil.addDropHandler(_this.dropTarget, function(dropEvent) {
                var dt;
                dt = dropEvent.dataTransfer;
                return _this.handleFiles(dt.files);
              });
              currentImageContainer = $('.current-images', _this.nidus);
              return currentImageContainer.click(function(event) {
                var el;
                el = $(event.target);
                if (el.hasClass('delete-image')) {
                  return _this.deleteImage(el.siblings('img'));
                }
              });
            });
          }
        });
      };

      PhotoUploadWidget.prototype.handleFiles = function(files) {
        var _this = this;
        return _.each(files, function(file) {
          if (!file.type.match(/image\/.*/)) {
            return;
          }
          return _this.renderImage(file);
        });
      };

      PhotoUploadWidget.prototype.renderImage = function(file) {
        var reader,
          _this = this;
        reader = new FileReader();
        reader.onload = function(event) {
          var image;
          image = new Image();
          image.src = event.target.result;
          return image.onload = function(event) {
            var canvas, context, img, scale;
            img = event.target;
            scale = Math.min(_this.dropTarget.width() / img.width, _this.dropTarget.height() / img.height);
            canvas = $('<canvas></canvas>')[0];
            canvas.width = img.width * scale;
            canvas.height = img.height * scale;
            context = canvas.getContext('2d');
            context.drawImage(image, 0, 0, canvas.width, canvas.height);
            return _this.dropTarget.before(canvas);
          };
        };
        return reader.readAsDataURL(file);
      };

      PhotoUploadWidget.prototype.uploadImages = function() {
        var BlobBuilder, elements, fileUploadWidget;
        BlobBuilder = BlobBuilder || WebKitBlobBuilder || MozBlobBuilder || MSBlobBuilder;
        elements = $('canvas', this.nidus);
        fileUploadWidget = this;
        return elements.each(function() {
          var binary, blob, blobBuilder, contentType, dataUrl, formData;
          dataUrl = this.toDataURL();
          binary = atob(dataUrl.replace(/^data:image\/(png|jpg);base64,/, ""));
          contentType = dataUrl.match(/image\/[^;]+/)[0];
          blobBuilder = new BlobBuilder();
          blobBuilder.append(new Uint8Array(Array.prototype.map.call(binary, function(c) {
            return c.charCodeAt(0) & 0xff;
          })).buffer);
          blob = blobBuilder.getBlob(contentType);
          formData = new FormData();
          formData.append('image', blob);
          formData.append('contentType', contentType);
          return $.ajax('/rest/images', {
            data: formData,
            contentType: false,
            processData: false,
            type: 'POST',
            complete: function(jqXHR, stat) {
              if (stat === 'success') {
                console.log("Fileupload successful");
                return fileUploadWidget.render();
              } else {
                return fileUploadWidget.reportError("Fileupload failed: " + jqXHR);
              }
            }
          });
        });
      };

      PhotoUploadWidget.prototype.deleteImage = function(image) {
        var url;
        url = image.attr('src');
        return $.ajax(url, {
          data: '',
          type: 'DELETE',
          complete: function(jqXHR, stat) {
            if (stat === 'success') {
              console.log("File deleted successfully");
              return image.parents('.current-image').remove();
            } else {
              return this.reportError("Failed to delete file: " + jqXHR);
            }
          }
        });
      };

      return PhotoUploadWidget;

    })(RoomrWidget);
  });

}).call(this);
