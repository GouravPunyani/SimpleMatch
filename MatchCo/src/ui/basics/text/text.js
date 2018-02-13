// @flow

import React from 'react'
import { Text as ReactNativeText, StyleSheet } from 'react-native'
import { fontFamily, color, palette } from '../../theme'
import { omit } from 'ramda'
import { translator } from '../../../services/translate/translator'

const withoutKnownProps = omit(['text', 'children', 'style', 'small', 'title', 'tx'])

export type Props = {
  text?: string,
  children?: React.Children,
  style?: any,
  small?: Boolean,
  title?: Boolean,
  fancy?: Boolean,
  brand?: Boolean,
  lighter? : Boolean,
  white?: Boolean,
  tx?: string,
  translate: (key: string) => string,
  center?: Boolean
};

export const Text = translator((props: Props) => {
  const style = [styles.text]
  if (props.small) {
    style.push(styles.small)
  }
  if (props.title) {
    style.push(styles.title)
  }
  if (props.style) {
    style.push(props.style)
  }
  if (props.fancy) {
    style.push(styles.fancy)
  }
  if (props.brand) {
    style.push(styles.brand)
  }
  if (props.lighter) {
    style.push(styles.lighter)
  }
  if (props.white) {
    style.push(styles.white)
  }
  if (props.center) {
    style.push(styles.center)
  }
  const textToUse = props.tx ? props.translate(props.tx) : props.text
  return (
    <ReactNativeText style={style} {...withoutKnownProps(props)}>
      {props.children ? props.children : textToUse}
    </ReactNativeText>
  )
})

const styles = StyleSheet.create({
  text: {
    color: color.text,
    fontSize: 14,
    fontFamily: fontFamily.nexa
  },
  small: { fontSize: 12 },
  title: {
    fontSize: 22,
    fontFamily: fontFamily.nexaBold
  },
  fancy: { fontFamily: fontFamily.sangBlueLightItalic },
  brand: { color: color.brand },
  lighter: { color: color.textLighter },
  white: { color: palette.white },
  center: { textAlign: 'center' }
})
