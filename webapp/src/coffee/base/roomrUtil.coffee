# just import moment-de in order to switch to german
define ['jquery', 'handlebars', 'underscore', 'moment', 'moment-de'], ($, Handlebars, _, moment, moment_de) ->
  'use strict'

  #Handlebars.registerHelper 'date', (date) -> new Date(date).toLocaleDateString()
  Handlebars.registerHelper 'date', (date) -> moment(date).format('D. MMMM YYYY')


  # TODO: It seems as if the cache doesn't work...
  templateCache = {}

  defaultErrorHandler = (templateName, options) ->
    $(window).trigger('templateLoadError', "received #{options.status} when requesting template #{templateName}")

  return {
    # Die **Render Template Funktion** rendert das übergebene Template und ruft nach erfolgtem
    # Render die übergebenen Callback-Funktion auf.
    # Usage: `renderTemplate(templateName, context, callback)`. `templateName` is the path of
    # the template starting at `/templates/` with the extension `.handlebars` removed. `context`
    # is used to replace placeholders in the template (`{ key: 'value' }). The function 
    # `callback` is invoked as soon as the template is loaded and after the placeholders are
    # filled. It takes the HTML string as a parameter. The function `errorCallback` is invoked
    # if any error occurs (e.g. the requested template could not be found) with the first
    # parameter being the templateName and the second parameter being te options element that
    # is returned by jQuery.
    renderTemplate: (templateName, context, callback, errorCallback = defaultErrorHandler) ->
      if templateCache[templateName]?
        callback(templateCache[templateName] context)
        return

      $.ajax {
        url: "/templates/#{templateName}.handlebars"
        type: 'GET'
        success: (tpl) ->
          template = Handlebars.compile(tpl)
          templateCache[templateName] = template
          callback(template context)
        error: _.bind errorCallback, this, templateName
      }

    postJson: (url, object, success, error) ->
      if !_.isString url
        throw "#{url} is not a string"
      if !_.isObject object
        throw "#{object} is not an object"
      if !_.isFunction success
        throw "#{success} is not a function"
      if !_.isFunction error
        throw "#{error} is not a function"

      $.ajax url, {
        contentType : "application/json"
        data: JSON.stringify object
        type: 'POST'
        success: success
        error: error
      }

    addTypingFinishedCallback: (elements, callback, doneTypingTimeout = 200) ->
      targets = $(elements)
      typingTimer = {}

      targets.each ->
        $(this).data 'oldVal', $(this).val()
        $(this).bind "propertychange keyup input paste", ->
          changed = false
          targets.each ->
            changed = changed || $(this).data('oldVal') != $(this).val()
            return true
          
          if (changed)
            targets.each ->
              $(this).data 'oldVal', $(this).val()
              return true

            clearTimeout typingTimer
            typingTimer = setTimeout callback, doneTypingTimeout

    addDropHandler: (element, callback) ->
      $(element).bind {
        # Drop-Operation ermöglichen
        dragover: (evt) ->
          evt.preventDefault()

        # Aktiv-Klasse bei Dropover
        dragenter: (evt) ->
          $(this).addClass 'active'
        dragleave: (evt) ->
          $(this).removeClass 'active'

        # Drop-Event
        drop: (evt) ->
          $(this).removeClass 'active'
          evt.preventDefault()
          callback.call(this, evt.originalEvent)
        }
  }