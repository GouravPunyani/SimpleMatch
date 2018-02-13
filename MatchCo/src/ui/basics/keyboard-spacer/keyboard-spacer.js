// @flow
import React from 'react'
import { View, Keyboard, KeyboardEventListener } from 'react-native'
import { bind } from 'decko'

export type KeyboardSpacerState = {
  keyboardHeight: number
}

export type KeyboardSpacerProps = {
  onShowing?: Function,
  onHiding?: Function,
  offset?: number
}

export class KeyboardSpacer
  extends React.Component<void, KeyboardSpacerProps, KeyboardSpacerState> {
  state = {
    keyboardHeight: 0
  }

  keyboardShowListener: KeyboardEventListener
  keyboardHideListener: KeyboardEventListener

  @bind handleKeyboardWillShow (event: KeyboardEventListener) {
    this.setState({ keyboardHeight: event.endCoordinates.height })
    this.props.onShowing && this.props.onShowing()
  }

  @bind handleKeyboardWillHide (event: KeyboardEventListener) {
    this.setState({ keyboardHeight: 0 })
    this.props.onHiding && this.props.onHiding()
  }

  componentWillMount () {
    this.keyboardShowListener = Keyboard.addListener(
      'keyboardWillShow',
      this.handleKeyboardWillShow
    )
    this.keyboardHideListener = Keyboard.addListener(
      'keyboardWillHide',
      this.handleKeyboardWillHide
    )
  }

  componentWillUnmount () {
    this.keyboardShowListener.remove()
    this.keyboardHideListener.remove()
  }

  render () {
    const offset = this.props.offset || 0
    const height = this.state.keyboardHeight + offset
    return <View style={{ height }} />
  }
}
