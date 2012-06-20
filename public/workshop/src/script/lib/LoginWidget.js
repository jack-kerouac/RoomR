(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  define(['PageView', 'lib/renderTemplate', 'lib/EventMediator'], function(PageView, renderTemplate, EventMediator) {
    'use strict';

    var LoginWidget;
    return LoginWidget = (function() {

      function LoginWidget() {
        this.renderLoggedIn = __bind(this.renderLoggedIn, this);

        this.renderLoggedOut = __bind(this.renderLoggedOut, this);
        EventMediator.subscribeToEvent('loggedIn', this.renderLoggedIn);
        EventMediator.subscribeToEvent('loggedOut', this.renderLoggedOut);
      }

      LoginWidget.prototype.render = function() {
        return this.renderLoggedOut();
      };

      LoginWidget.prototype.renderLoggedOut = function() {
        return renderTemplate('login', {}, function(content) {
          if (this.openItem != null) {
            this.openItem.remove();
          }
          return new PageView().render('LOG DICH EIN!', content);
        });
      };

      LoginWidget.prototype.renderLoggedIn = function() {
        return renderTemplate('profileInfo', {}, function(content) {
          if (this.openItem != null) {
            this.openItem.remove();
          }
          return new PageView().render('Hi there', content);
        });
      };

      return LoginWidget;

    })();
  });

}).call(this);
