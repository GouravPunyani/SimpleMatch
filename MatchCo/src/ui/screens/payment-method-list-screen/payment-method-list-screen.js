// @flow

import React, { Component } from 'react'
import { View, Text, StyleSheet } from 'react-native'
import { Button, ButtonPanel } from '../..'

export class PaymentMethodListScreen extends Component {
  static propTypes: {
    navigation: any
  }

  render () {
    const { navigation } = this.props
    const navigate = (path, options = {}) => () => navigation.navigate(path, options)

    return (
      <View style={styles.wrapper}>
        <Text style={styles.text}>PaymentMethodListScreen</Text>
        <Text style={styles.target}>src/ui/screens/payment-method-list-screen/payment-method-list-screen.js</Text>
        <ButtonPanel>
          <Button text='Payment Method' onPress={navigate('paymentMethod')} />
        </ButtonPanel>
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
