// @flow
import React, { Component } from 'react'
import { View, StyleSheet } from 'react-native'
import { ProductTabBarButton } from '../..'
import { translator } from '../../../services/translate/translator'
import { color } from '../../theme'
import { bind } from 'decko'

export type Props = {
  translate: (key: string) => string,
  onChange: (index: number) => void
}

export type State = {
  selection: number
}

@translator
export class ProductTabBar extends Component<*, Props, State> {
  state = {
    selection: 0
  }

  @bind
  onPressDescription () {
    this.setState({ selection: 0 })
    this.props.onChange(0)
  }
  
  @bind
  onPressIngredients () {
    this.setState({ selection: 1 })
    this.props.onChange(1)
  }

  @bind
  onPressTestimonials () {
    this.setState({ selection: 2 })
    this.props.onChange(2)
  }

  render () {
    const { translate } = this.props
    const { selection } = this.state

    return (
      <View style={styles.wrapper}>
        <ProductTabBarButton
          selected={selection === 0}
          text={translate('product.tabDescription')}
          onPress={this.onPressDescription}
          rightBorder
        />
        <ProductTabBarButton
          selected={selection === 1}
          text={translate('product.tabIngredients')}
          onPress={this.onPressIngredients}
          rightBorder
        />
        <ProductTabBarButton
          selected={selection === 2}
          onPress={this.onPressTestimonials}
          text={translate('product.tabTestimonials')}
        />
      </View>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: {
    flex: 1,
    flexDirection: 'row',
    borderWidth: 1,
    borderColor: color.line,
    borderRadius: 2
  }
})
