// @flow

import React, { Component } from 'react'
import { View, StyleSheet } from 'react-native'
import { spacing, color } from '../../theme'

export type Props = {
  children: React.Children,
  style?: ViewStyle
};

export class StaticLayout extends Component <*, Props, *> {
  render () {
    return (
      <View style={[styles.wrapper, this.props.style]}>
        {this.props.children}
      </View>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: {
    flex: 1,
    backgroundColor: color.background,
    justifyContent: 'flex-start',
    alignItems: 'stretch',
    padding: spacing.medium
  }
})
