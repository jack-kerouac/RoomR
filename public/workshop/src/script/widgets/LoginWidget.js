(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(['base/renderTemplate', 'base/RoomrWidget'], function(renderTemplate, RoomrWidget) {
    'use strict';

    var LoginWidget;
    return LoginWidget = (function(_super) {

      __extends(LoginWidget, _super);

      function LoginWidget() {
        this.renderLogoutForm = __bind(this.renderLogoutForm, this);

        this.renderLoginForm = __bind(this.renderLoginForm, this);

        this.logInUser = __bind(this.logInUser, this);

        var _this = this;
        LoginWidget.__super__.constructor.call(this, 'login');
        this.loginState = 'unknown';
        this.userName = 'foo';
        this.userEmail = 'foo';
        this.registerPropChgEvent('loginStateChanged');
        this.registerPropChgEvent('userNameChanged');
        this.registerPropChgEvent('userEmailChanged');
        this.registerPropChgEvent('userBirthdate');
        this.registerPropChgEvent('userGender');
        this.subscribeToEvent('newUserCreated', function(newUser) {
          return _this.logInUser(newUser.email, newUser.password);
        });
        this.subscribeToEvent('loginStateChanged', function(newState) {
          _this.loginState = newState;
          return _this.render();
        });
        this.subscribeToEvent('userNameChanged', function(newName) {
          return _this.userName = newName;
        });
        this.subscribeToEvent('userEmailChanged', function(newEmail) {
          return _this.userEmail = newEmail;
        });
        this.findOutState();
      }

      LoginWidget.prototype.emitUserInfoEvents = function(userInfo) {
        this.emit('userNameChanged', userInfo.name);
        this.emit('userEmailChanged', userInfo.email);
        this.emit('userBirthdate', userInfo.birthdate);
        this.emit('userGender', userInfo.gender);
        return this.emit('loginStateChanged', 'loggedIn');
      };

      LoginWidget.prototype.findOutState = function() {
        var _this = this;
        return $.ajax({
          url: '/rest/users/current',
          complete: function(jqXHR, stat) {
            if (stat === 'success') {
              return _this.emitUserInfoEvents(JSON.parse(jqXHR.responseText));
            } else {
              return _this.emit('loginStateChanged', 'loggedOut');
            }
          }
        });
      };

      LoginWidget.prototype.logInUser = function(email, password) {
        var postData,
          _this = this;
        postData = {
          email: email,
          password: password
        };
        return $.ajax('/rest/login', {
          contentType: "application/json",
          data: JSON.stringify(postData),
          type: 'POST',
          complete: function(jqXHR, stat) {
            var userInfo;
            if (stat === 'success') {
              userInfo = JSON.parse(jqXHR.responseText);
              if (userInfo.error != null) {
                return alert("Fehler beim Login");
              } else {
                return _this.emitUserInfoEvents(userInfo);
              }
            } else {
              return alert("Fehler beim Login");
            }
          }
        });
      };

      LoginWidget.prototype.setLoginSubmitEvent = function() {
        var _this = this;
        return $('#LoginWidgetForm').submit(function(event) {
          var email, password;
          event.preventDefault();
          email = $('#LoginWidgetForm input[name=email]').val();
          password = $('#LoginWidgetForm input[name=password]').val();
          return _this.logInUser(email, password);
        });
      };

      LoginWidget.prototype.setLogoutSubmitEvent = function() {
        var _this = this;
        return $('#LogoutWidgetForm').submit(function(event) {
          var postData;
          event.preventDefault();
          postData = {
            msg: "I'm off"
          };
          return $.ajax('/rest/logout', {
            contentType: "application/json",
            data: JSON.stringify(postData),
            type: 'POST',
            complete: function(jqXHR, stat) {
              if (stat === 'success') {
                _this.emit('userNameChanged', '');
                _this.emit('userEmailChanged', '');
                _this.emit('userBirthdate', '');
                _this.emit('userGender', '');
                return _this.emit('loginStateChanged', 'loggedOut');
              } else {
                return console.log("Kaputt", jqXHR);
              }
            }
          });
        });
      };

      LoginWidget.prototype.renderInto = function(elem) {
        this.elem = elem;
        return this.render();
      };

      LoginWidget.prototype.render = function() {
        if (this.elem != null) {
          if (this.loginState === 'loggedIn') {
            return this.renderLogoutForm();
          } else {
            return this.renderLoginForm();
          }
        }
      };

      LoginWidget.prototype.renderLoginForm = function() {
        var _this = this;
        return this.renderTemplate({}, function(html) {
          $(_this.elem).empty();
          $(_this.elem).append(html);
          return _this.setLoginSubmitEvent();
        }, 'login');
      };

      LoginWidget.prototype.renderLogoutForm = function() {
        var _this = this;
        return this.renderTemplate({
          UserName: this.userName,
          UserEmail: this.userEmail
        }, function(html) {
          $(_this.elem).empty();
          $(_this.elem).append(html);
          return _this.setLogoutSubmitEvent();
        }, 'logout');
      };

      return LoginWidget;

    })(RoomrWidget);
  });

}).call(this);
