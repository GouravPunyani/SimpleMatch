import { Component, Children } from 'react'
import PropTypes from 'prop-types'
import './translate-setup'
import I18n from 'react-native-i18n'

export type Props = {
  translate: (key: string) => string
};

/**
 * A TranslateProvider will allow the translate() HOC to work.
 */
export class TranslateProvider extends Component<*, Props, *> {
  /**
   * The context prop values.
   */
  getChildContext () {
    return {
      translate: (key: string) => I18n.t(key)
    }
  }

  render () {
    return Children.only(this.props.children)
  }
}

/**
 * Register the permitted props via context.
 */
TranslateProvider.childContextTypes = {
  translate: PropTypes.func.isRequired
}
