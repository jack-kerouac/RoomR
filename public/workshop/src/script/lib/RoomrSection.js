(function() {

  define(['backbone', 'lib/renderTemplate'], function(Backbone, renderTemplate) {
    return Backbone.View.extend({
      el: '#Main > section',
      initialize: function(options) {
        this.widgets = [];
        this.title = options.title;
        return this.name = options.name;
      },
      addWidget: function(widget) {
        return this.widgets[widget.name] = widget;
      },
      render: function(callback) {
        var _this = this;
        $('title').text("RoomR - " + this.title);
        $('#Headline').html(this.title);
        renderTemplate("sections/" + this.name, {}, function(template) {
          _this.$el.html(template);
          _this.$el.attr('id', "" + _this.name + "-section");
          _this.$('.widget').each(function(index, element) {
            var widget, widgetType;
            widgetType = $(element).attr('data-type');
            widget = _this.widgets[widgetType];
            if (widget != null) {
              return widget.renderInto(element);
            } else {
              return alert("no widget found for <... data-type=\"" + widgetType + "\">");
            }
          });
          if (callback != null) {
            return callback();
          }
        });
        return this;
      }
    });
  });

}).call(this);
