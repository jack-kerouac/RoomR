# Helper for mocking the Jquery `ajax` method. Upon `mockAjax`, the `ajax` method of Jquery is replaced.
# as soon as the request should return, `respond` must be called. The url that has been called upon the last
# ajax call can be retrieved with the `url` property.

define ->
  options = null

  {
    mockAjax: ->
      # override ajax call of jquery and assign passed `param` object to `options`
      $.ajax = (param) ->
        options = param
     
    respond: (content) ->
      # trigger end of ajax call
      options.success content

    url: ->
      options.url
  }