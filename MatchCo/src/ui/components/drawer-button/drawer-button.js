// @flow

import React, { Component } from 'react'
import { StyleSheet, TouchableOpacity } from 'react-native'
import { fontFamily, color, spacing } from '../../theme'
import { Text } from '../..'

export class DrawerButton extends Component {
  static propTypes: {
    text: string,
    onPress: Function
  }

  render () {
    return (
      <TouchableOpacity style={styles.wrapper} onPress={this.props.onPress}>
        <Text style={styles.text} text={this.props.text} />
      </TouchableOpacity>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: {
    justifyContent: 'center',
    paddingVertical: spacing.medium
  },
  text: {
    color: color.drawerText,
    fontFamily: fontFamily.nexaLight
  }
})
