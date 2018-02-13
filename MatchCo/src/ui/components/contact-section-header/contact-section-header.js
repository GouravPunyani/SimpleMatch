// @flow

import React from 'react'
import { View, StyleSheet } from 'react-native'
import { Text } from '../..'
import { fontFamily, color, spacing } from '../../theme'

export type Props = {
  text: string
};

export function ContactSectionHeader (props: Props): React.Children {
  return (
    <View style={styles.wrapper}>
      <Text style={styles.text}>{props.text}</Text>
    </View>
  )
}

const styles = StyleSheet.create({
  wrapper: {
    borderBottomWidth: 1,
    borderBottomColor: color.brand,
    paddingBottom: spacing.extraSmall,
    marginBottom: spacing.extraExtraSmall
  },
  text: {
    fontSize: 16,
    fontFamily: fontFamily.sangBlueLightItalic
  }
})
