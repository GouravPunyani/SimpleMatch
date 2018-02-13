// @flow

import React, { Component } from 'react'
import { View, Text, StyleSheet } from 'react-native'

export class ScanScreen extends Component {
  static propTypes: {
  }

  render () {
    return (
      <View style={styles.wrapper}>
        <Text style={styles.text}>ScanScreen</Text>
        <Text style={styles.target}>src/ui/screens/scan-screen/scan-screen.js</Text>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: {
    flex: 1,
    backgroundColor: 'rebeccapurple',
    justifyContent: 'center',
    alignItems: 'center'
  },
  text: { color: 'white', fontSize: 30 },
  target: { color: 'white', fontSize: 12 }
})
