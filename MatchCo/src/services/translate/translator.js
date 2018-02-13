// @flow
// import type { HigherOrderComponent } from 'react-flow-types'
// import type { ReactClass } from 'react'
import React, { Component } from 'react'
import PropTypes from 'prop-types'

/**
 * This is higher-order component which gives all components that opt-in to
 * have access to a function called translate.
 *
 * @param {ReactComponent} ComponentToWrap - The component to bestow.
 */
export const translator = (ComponentToWrap: ReactClass<any>) => {
  /**
   * The HOC wrapper class.
   */
  class ComponentWithTranslator extends Component {
    render () {
      const { props, context: { translate } } = this
      return <ComponentToWrap {...props} translate={translate} />
    }
  }

  // register the context properties made available
  ComponentWithTranslator.contextTypes = {
    translate: PropTypes.func.isRequired
  }

  return ComponentWithTranslator
}
