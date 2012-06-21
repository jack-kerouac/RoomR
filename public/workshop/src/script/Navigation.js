(function() {

  define(['PageView', 'UserView', 'base/renderTemplate', 'widgets/SearchWidget'], function(PageView, UserView, renderTemplate, SearchWidget) {
    var Navigation;
    return Navigation = (function() {

      Navigation.prototype.users = null;

      Navigation.prototype.app = null;

      function Navigation(users) {
        this.users = users;
      }

      Navigation.prototype.showStart = function() {
        return renderTemplate('start', {}, function(content) {
          if (this.openItem != null) {
            this.openItem.remove();
          }
          return new PageView().render('Startseite', content);
        });
      };

      Navigation.prototype.showAbout = function() {
        return renderTemplate('about', {
          date: Date().toString()
        }, function(content) {
          if (this.openItem != null) {
            this.openItem.remove();
          }
          return new PageView().render('Über dieses Beispiel', content);
        });
      };

      Navigation.prototype.showItems = function() {
        var _this = this;
        return renderTemplate('view', {}, function(content) {
          new PageView().render('Einträge', content);
          return _this.users.fetch({
            success: function(collection) {
              return collection.each(function(userItem) {
                var itemView;
                itemView = new UserView({
                  tagName: 'li',
                  model: userItem,
                  template: 'list'
                });
                itemView.delegateEvents({
                  'click .item-title': function(evt) {
                    var id;
                    id = $(evt.target).attr('id').split('-').pop();
                    return _this.app.navigate("#view/" + id, true);
                  }
                });
                userItem.fetch();
                return userItem.on('change', function() {
                  return itemView.render('#ItemsList');
                });
              });
            }
          });
        });
      };

      Navigation.prototype.showSingleItem = function(id) {
        var item;
        if (id != null) {
          item = this.users.find(function(model) {
            return model.get('id') === id;
          });
          if (item != null) {
            if (typeof openItem !== "undefined" && openItem !== null) {
              openItem.remove();
            }
            this.openItem = new UserView({
              model: item,
              className: 'item-bigview',
              template: 'full'
            });
            this.openItem.delegateEvents({
              'click .close-item': function() {
                this.remove();
                return app.navigate("#view");
              }
            });
            return this.openItem.render('body');
          }
        }
      };

      return Navigation;

    })();
  });

}).call(this);
