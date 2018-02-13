// @flow
import type { ViewStyle, TextStyle } from 'react-native'
import React from 'react'
import { TouchableOpacity } from 'react-native'
import { Text } from '../..'
import { palette, color, spacing, fontFamily } from '../../theme'
import { translator } from '../../../services/translate/translator'
import glam from 'glamorous-native'
import { mergeStyles } from '../../../lib/merge-styles'
import { firebase } from 'firebase'

/**
 * The button properties.
 */
export type ButtonProps = {
  /** The text  */
  text?: string,
  /** Use this translation key to write the text. */
  tx?: string,
  /** Translates the given key into the appropriate language. */
  translate: (key: string) => string,
  /** Fires when the button is pressed. */
  onPress: Function,
  /** Styles applied to the wrapper. */
  style?: ViewStyle,
  /** Styles applied to the text. */
  textStyle?: TextStyle,
  /** Is interaction disabled? */
  disabled: Boolean
};

/**
 * It's a button.  Hooray!
 */
export type ButtonType = {
  /**
   * A button that blends into the background.
   */
  Clear: React.SFC<ButtonProps>,

  /**
   * A button with Facebook's brand color.
   */
  Facebook: React.SFC<ButtonProps>
};

/**
 * The outer-most component which forms the base of the button.
 */
const Wrapper: TouchableOpacity = glam(TouchableOpacity)(
  {
    minHeight: 44,
    minWidth: 44,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: color.buttonBackground,
    paddingHorizontal: spacing.medium,
    marginVertical: spacing.small,
    borderRadius: 2,
    shadowRadius: 1,
    shadowOffset: { width: 1, height: 1 },
    shadowColor: palette.black,
    shadowOpacity: 0.5
  },
  (props: ButtonProps) => mergeStyles(
    props.style,
    props.disabled && { backgroundColor: color.buttonBackgroundDisabled }
  )
)

/**
 * The text displayed on the button.
 */
const ButtonText: Text = glam(Text)(
  {
    color: color.buttonText,
    fontFamily: fontFamily.nexaBold,
    fontSize: 15
  },
  (props: ButtonProps) => mergeStyles(
    props.style,
    props.disabled && { color: color.buttonTextDisabled }
  )
)

/**
 * It's a button.  Hooray!
 */
export const Button: ButtonType = translator((props: ButtonProps): React.SFC<
  ButtonProps
> => {
  const { style, textStyle, onPress, disabled, tx, text, translate } = props

  return (
    <Wrapper style={style} onPress={onPress} disabled={disabled}>
      <ButtonText style={textStyle} disabled={disabled}>
        {tx ? translate(tx) : text}
      </ButtonText>
    </Wrapper>
  )
})

Button.Clear = (props: ButtonProps) => (
  <Button
    {...props}
    style={{ backgroundColor: color.backgroundColor }}
    textStyle={{ color: color.brand }}
  />
)

Button.Facebook = (props: ButtonProps) => (
  <Button
    {...props}
    style={{ backgroundColor: palette.facebook }}
    textStyle={{ color: palette.white }}
  />
)
