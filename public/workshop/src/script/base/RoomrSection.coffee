# A section is a view of the website addressable by a URL. It can thus be bookmarked. It 
# is rendered into the `section` element below the `#Main` element.
# It contains a set of widgets that are added with `addWidget`.
# Each section is associated with a template in the folder `sections/`. `<div>` elements
# in the template that have the class `widget` set will be used as the containers for 
# widgets. Their `data-widget-name` attribute defines the name of the widget that is
# rendered into this `div`.

define ['backbone', 'base/renderTemplate'], (Backbone, renderTemplate) ->

  return Backbone.View.extend {

    el: '#Main > section'

    initialize: (options) ->
      @widgets = []
      @title = options.title
      @name = options.name
      @path = if options.path? then options.path else options.name

    addWidget: (widget) ->
      @widgets[widget.name] = widget;

    render: (callback) ->
      $('title').text("RoomR - #{@title}")
      $('#Headline').html(@title)

      successCallback = (template) =>
        @$el.html(template)
        @$el.attr('id', "#{@name}-section")
        this.$('.widget').each (index, element) =>
          widgetType = $(element).attr('data-type')
          widget = @widgets[widgetType]
          if widget?
            widget.renderInto(element)
          else
            alert "no widget found for <... data-type=\"#{widgetType}\">"
        if callback? then callback()

      errorCallback = (templateName, options) ->
        alert 'failure'

      renderTemplate "sections/#{@name}", {}, successCallback, errorCallback
      
      return this
  }