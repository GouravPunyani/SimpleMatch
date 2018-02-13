// @flow
import React, { Component } from 'react'
import { Text } from '../..'
import glam from 'glamorous-native'
import { color, spacing, fontFamily } from '../../theme'
import { bind } from 'decko'
import { isEmpty, isNil } from 'ramda'

// a value we set to ensure the text doesn't move when there's no errors
const EMPTY = ' '

export type TextFieldProps = {
  children?: React.Children,
  labelTx?: string,
  label?: string,
  errors?: string[],
  autoCorrect?: boolean,
  autoCapitalize?: 'none' | 'sentences' | 'words' | 'characters',
  maxLength?: number,
  password?: boolean,
  disabled?: boolean,
  entity: any,
  field: string,
  value: string,
  update: (value: string) => void,
  errorVisibility?: 'always' | 'afterFirstBlur'
};
export type TextFieldState = {
  hasFocus?: boolean,
  hasBeenBlurredOnce: boolean
};

const Wrapper = glam.view({
  paddingTop: spacing.small,
  paddingBottom: spacing.none
})

const Input = glam.textInput({
  width: '100%',
  height: 35,
  backgroundColor: 'transparent',
  paddingTop: spacing.small,
  fontFamily: fontFamily.nexa,
  fontSize: 15
})

const Label = glam(Text)({
  position: 'absolute',
  backgroundColor: 'transparent'
}, props => ({
  fontSize: props.hasValue || props.hasFocus ? 12 : 15,
  top: props.hasValue || props.hasFocus ? spacing.none : 20,
  color: props.hasFocus ? color.brand : color.textLighter
}))

const Line = glam.view({
  height: 1
}, props => ({
  backgroundColor: props.error ? color.error : color.lineLight
}))

const ErrorText = glam(Text)({
  color: color.error
})

export class TextField extends Component<TextFieldProps, TextFieldProps, TextFieldState> {
  state = {
    hasFocus: false,
    hasBeenBlurredOnce: false
  }

  static defaultProps = {
    autoCorrect: false,
    autoCapitalize: 'none',
    disabled: false
  }

  @bind onFocus () {
    this.setState({ hasFocus: true })
  }

  @bind onBlur () {
    const { entity, field } = this.props
    entity.validate(field)
    this.setState({ hasFocus: false, hasBeenBlurredOnce: true })
  }

  @bind onChangeText (text: string) {
    const { entity, field } = this.props
    entity.update(field, text)
  }

  render () {
    const { value, errors, errorVisibility = 'afterFirstBlur' } = this.props
    const { hasFocus, hasBeenBlurredOnce } = this.state
    const hasValue = !isNil(value) && !isEmpty(value)
    const hasError = errors && errors.length > 0
    let errorMessage = hasError ? errors[0] : EMPTY
    let showError = hasError && !hasFocus

    // suppress errors from showing until after the user left the field for the first time
    if (errorVisibility === 'afterFirstBlur' && !hasBeenBlurredOnce) {
      showError = showError && hasBeenBlurredOnce
      errorMessage = EMPTY
    }

    return (
      <Wrapper>
        <Label
          small
          tx={this.props.labelTx}
          text={this.props.label}
          hasFocus={hasFocus}
          hasValue={hasValue}
        />
        <Input
          editable={!this.props.disabled}
          onFocus={this.onFocus}
          onBlur={this.onBlur}
          onChangeText={this.onChangeText}
          autoCorrect={this.props.autoCorrect}
          autoCapitalize={this.props.autoCapitalize}
          maxLength={this.props.maxLength}
          value={value}
          secureTextEntry={this.props.password === true}
        />
        <Line error={showError} />
        <ErrorText small text={errorMessage} />
      </Wrapper>
    )
  }
}
