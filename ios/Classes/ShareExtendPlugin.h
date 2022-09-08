#import <Flutter/Flutter.h>

@protocol ShareExtendPluginDelegate <NSObject>
@optional
- (UIViewController*)presentingViewControllerForShareExtend;
@end

@interface ShareExtendPlugin : NSObject<FlutterPlugin>
@end
