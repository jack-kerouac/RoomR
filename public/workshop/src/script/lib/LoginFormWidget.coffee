define ['PageView', 'lib/renderTemplate'], (PageView, renderTemplate) ->
  'use strict'

  class LoginFormWidget
    render: (idOfParentElement) ->
      renderTemplate 'login', {}, (content) ->
        if @openItem? then @openItem.remove()
        new PageView().render('LOG DICH EIN!', content)
