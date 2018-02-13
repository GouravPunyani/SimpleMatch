// @flow

import React, { Component } from 'react'
import { View, StyleSheet, FlatList } from 'react-native'
import { Testimonial } from '../..'
import {
  Testimonial as TestimonialEntity
} from '../../../entities/testimonial'
import Video from 'react-native-video'
import { bind } from 'decko'

export type Props = {
  testimonials: Array<TestimonialEntity>
}

export type State = {
  videoUrl: ?string
}

export class TestimonialList extends Component<*, Props, State> {
  player: *

  state = {
    videoUrl: null
  }

  @bind
  onPressTestimonial (testimonial: TestimonialEntity) {
    // this.setState({ videoUrl: testimonial.videoUrl })
  }

  @bind
  onVideoEnd () {
    // this.setState({ videoUrl: null })
  }

  @bind
  onVideoError () {
    // this.setState({ videoUrl: null })
  }

  render () {
    const renderItem = (row: *) => {
      const testimonial: TestimonialEntity = row.item
      return (
        <Testimonial
          key={testimonial.id}
          reverse={row.index % 2 !== 0}
          testimonial={testimonial}
          onPress={() => this.onPressTestimonial(testimonial)}
        />
      )
    }

    const playVideo = typeof this.state.videoUrl === 'string'

    return (
      <View style={styles.wrapper}>
        <FlatList
          data={this.props.testimonials}
          renderItem={renderItem}
          keyExtractor={(item, index) => item.id}
        />
        {playVideo &&
          <Video
            ref={(ref) => {
              this.player = ref
              // this.player.presentFullscreenPlayer()
            }}
            playInBackground={false}
            repeat={false}
            onEnd={this.onVideoEnd}
            onError={this.onVideoError}
            resizeMode='cover'
            source={{ uri: null }}
            style={styles.backgroundVideo}
          />
        }
      </View>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: {
    flex: 1
  },
  text: {
    fontSize: 30
  },
  backgroundVideo: {
    zIndex: 1,
    position: 'absolute',
    top: 0,
    left: 0,
    bottom: 0,
    right: 0
  }
})
