// @flow

import React, { Component } from 'react'
import { View, Text, StyleSheet } from 'react-native'

export class PaymentMethodScreen extends Component {
  static propTypes: {
    navigation: any
  }

  render () {
    return (
      <View style={styles.wrapper}>
        <Text style={styles.text}>PaymentMethodScreen</Text>
        <Text style={styles.target}>src/ui/screens/payment-method-screen/payment-method-screen.js</Text>
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
