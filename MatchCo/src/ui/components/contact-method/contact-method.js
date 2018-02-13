// @flow

import React from 'react'
import { View, StyleSheet, Image, TouchableOpacity } from 'react-native'
import { Text } from '../..'
import { fontFamily, color, spacing } from '../../theme'

export type Props = {
  icon: string,
  text: string,
  onPress: () => *
};

const envelope = require('./envelope.png')
const phone = require('./phone.png')

export function ContactMethod (props: Props): React.Children {
  const icon = props.icon === 'phone' ? phone : envelope
  return (
    <TouchableOpacity style={styles.wrapper} onPress={props.onPress}>
      <View style={styles.imageWrap}>
        <Image style={styles[props.icon]} source={icon} resizeMode='contain' />
      </View>
      <Text style={styles.text}>{props.text}</Text>
    </TouchableOpacity>
  )
}

const styles = StyleSheet.create({
  wrapper: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'flex-start',
    paddingVertical: spacing.extraSmall
  },
  imageWrap: {
    minWidth: 35,
    paddingRight: spacing.extraSmall
  },
  envelope: { height: 23, width: 35 },
  phone: { height: 34, width: 34 },
  text: {
    paddingLeft: spacing.extraSmall,
    color: color.brand,
    fontFamily: fontFamily.nexaBold
  }
})
