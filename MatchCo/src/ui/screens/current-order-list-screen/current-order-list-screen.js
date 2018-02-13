// @flow

import React, { Component } from 'react'
import { View, Text, StyleSheet } from 'react-native'

export class CurrentOrderListScreen extends Component {
  static propTypes: {
  }

  render () {
    return (
      <View style={styles.wrapper}>
        <Text style={styles.text}>CurrentOrderListScreen</Text>
        <Text style={styles.target}>src/ui/screens/current-order-list-screen/current-order-list-screen.js</Text>
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
