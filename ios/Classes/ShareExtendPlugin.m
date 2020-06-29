#import "ShareExtendPlugin.h"

@implementation ShareExtendPlugin

+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
    FlutterMethodChannel* shareChannel = [FlutterMethodChannel
                                          methodChannelWithName:@"com.zt.shareextend/share_extend"
                                          binaryMessenger:[registrar messenger]];
    
    [shareChannel setMethodCallHandler:^(FlutterMethodCall *call, FlutterResult result) {
        if ([@"share" isEqualToString:call.method]) {
            NSDictionary *arguments = [call arguments];
            NSArray *array = arguments[@"list"];
            NSString *shareType = arguments[@"type"];
            NSString *subject = arguments[@"subject"];
            
            if (array.count == 0) {
                result(
                       [FlutterError errorWithCode:@"error" message:@"Non-empty list expected" details:nil]);
                return;
            }
            
            NSNumber *originX = arguments[@"originX"];
            NSNumber *originY = arguments[@"originY"];
            NSNumber *originWidth = arguments[@"originWidth"];
            NSNumber *originHeight = arguments[@"originHeight"];
            
            CGRect originRect = CGRectZero;
            if (originX != nil && originY != nil && originWidth != nil && originHeight != nil) {
                originRect = CGRectMake([originX doubleValue], [originY doubleValue],
                                        [originWidth doubleValue], [originHeight doubleValue]);
            }
        
            if ([shareType isEqualToString:@"text"]) {
                [self share:array atSource:originRect withSubject:subject];
                result(nil);
            }  else if ([shareType isEqualToString:@"image"]) {
                NSMutableArray * imageArray = [[NSMutableArray alloc] init];
                for (NSString * path in array) {
                    UIImage *image = [UIImage imageWithContentsOfFile:path];
                    [imageArray addObject:image];
                }
                [self share:imageArray atSource:originRect withSubject:subject];
            } else {
                NSMutableArray * urlArray = [[NSMutableArray alloc] init];
                for (NSString * path in array) {
                    NSURL *url = [NSURL fileURLWithPath:path];
                    [urlArray addObject:url];
                }
                [self share:urlArray atSource:originRect withSubject:subject];
                result(nil);
            }
        } else {
            result(FlutterMethodNotImplemented);
        }
    }];
}

+ (void)share:(NSArray *)sharedItems atSource:(CGRect)origin withSubject:(NSString *) subject {
    UIActivityViewController *activityViewController = [[UIActivityViewController alloc] initWithActivityItems:sharedItems applicationActivities:nil];
    
    UIViewController *controller =[UIApplication sharedApplication].keyWindow.rootViewController;
    activityViewController.popoverPresentationController.sourceView = controller.view;

    if (CGRectIsEmpty(origin)) {
        origin = CGRectMake(0, 0, controller.view.bounds.size.width, controller.view.bounds.size.width /2);
    }
    activityViewController.popoverPresentationController.sourceRect = origin;

    [activityViewController setValue:subject forKey:@"subject"];

    [controller presentViewController:activityViewController animated:YES completion:nil];
}

@end
