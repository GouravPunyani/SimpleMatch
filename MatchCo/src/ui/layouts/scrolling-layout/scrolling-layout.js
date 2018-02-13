// @flow

import React, { Component } from 'react'
import { ScrollView, StyleSheet } from 'react-native'
import { color, spacing } from '../../theme'

export type ScrollingLayoutProps = {
  children?: React.Children,
  containerContentStyle?: any
};

export class ScrollingLayout
  extends Component<void, ScrollingLayoutProps, void> {
  render () {
    const style = [styles.wrapper]
    const containerContentStyle = [styles.containerContent]
    if (this.props.style) {
      style.push(this.props.style)
    }
    if (this.props.containerContentStyle) {
      containerContentStyle.push(this.props.containerContentStyle)
    }
    return (
      <ScrollView
        style={style}
        contentContainerStyle={containerContentStyle}
        keyboardShouldPersistTaps='handled'
      >
        {this.props.children}
      </ScrollView>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: {
    flex: 1,
    backgroundColor: color.background
  },
  containerContent: {
    padding: spacing.medium
  }
})
