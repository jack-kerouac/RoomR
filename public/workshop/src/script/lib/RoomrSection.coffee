define ['backbone', 'lib/renderTemplate'], (Backbone, renderTemplate) ->

  return Backbone.View.extend {

    el: '#Main > section'

    initialize: (options) ->
      @widgets = []
      @title = options.title
      @templateUrl = options.templateUrl

    addWidget: (id, widget) ->
      @widgets[id] = widget;

    # `render()` ist die View-Methode für die Ausgabe. Standardmäßig macht `render()` gar
    # nichts, d.h. man *muss* für jeden View eine eigene Methode schreiben. Diese Variante
    # nimmt einen Titel und Inhalt an und befüllt damit die entsprechenden Elemente.
    render: () ->
      $('title').text("RoomR - #{@title}")
      $('#Headline').html(@title)

      renderTemplate @templateUrl, {}, (template) =>
        @$el.html(template)
        this.$('.widget').each (index, element) =>
          widgetType = element.attr('data-type')
          # widget = _.find(@widgets, (widget) -> widget.id == widgetType)
          widget = @widgets[widgetType]
          if widget?
            widget.renderInto(element)
          else
            alert "no widget found for <... data-type=\"#{widgetType}\">"
      
      return this
  }