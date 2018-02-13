// @flow

import React from 'react'
import { StyleSheet, Image, TouchableOpacity } from 'react-native'

export type Props = {
  network: 'facebook' | 'instagram' | 'twitter',
  onPress: () => *
};

const icons = {
  facebook: require('./facebook.png'),
  instagram: require('./instagram.png'),
  twitter: require('./twitter.png')
}

export function ContactSocialButton (props: Props): React.Children {
  return (
    <TouchableOpacity style={styles.wrapper} onPress={props.onPress}>
      <Image
        style={styles.icon}
        source={icons[props.network]}
        resizeMode='contain'
      />
    </TouchableOpacity>
  )
}

const styles = StyleSheet.create({
  wrapper: {},
  icon: { width: 38, height: 38 }
})
