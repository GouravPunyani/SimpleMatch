// @flow
import React, { Component } from 'react'
import { View, StyleSheet } from 'react-native'

export default class ButtonPanel extends Component {
  render () {
    return (
      <View style={styles.wrapper}>
        {this.props.children}
      </View>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: {
    paddingVertical: 10
  }
})
